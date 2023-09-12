package com.cardmonix.cardmonix.service.Implementation.accountImpl;

import com.cardmonix.cardmonix.domain.entity.account.Account;
import com.cardmonix.cardmonix.domain.entity.account.Banks;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.AccountRespository;
import com.cardmonix.cardmonix.domain.repository.BankRespository;
import com.cardmonix.cardmonix.eceptions.AccountNotFoundException;
import com.cardmonix.cardmonix.eceptions.BankNotFoundException;
import com.cardmonix.cardmonix.request.*;
import com.cardmonix.cardmonix.response.*;
import com.cardmonix.cardmonix.service.AccountService;
import com.cardmonix.cardmonix.service.Implementation.authImpl.AuthServiceImpl;
import com.cardmonix.cardmonix.utils.PaystactAPIUtils;
import com.cardmonix.cardmonix.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static com.cardmonix.cardmonix.utils.ASCIIColors.ANSI_BLUE;
import static com.cardmonix.cardmonix.utils.ASCIIColors.ANSI_PURPLE;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements ApplicationRunner,  AccountService {
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;
    private final BankRespository bankRespository;
    private final AccountRespository accountRespository;
    private final AuthServiceImpl appUserService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;




    @Scheduled(cron = "0 0 8 * * ?")
    private void runScheduleTask(){
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cache-Control", "no-cache");
        RequestEntity<?>  requestEntity = new RequestEntity<>(headers,HttpMethod.GET, URI.create("https://api.paystack.co/bank"));
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity,String.class);
            BanksResponse bankData = objectMapper.readValue(responseEntity.getBody(), BanksResponse.class);
            List<BankData> bankDataList = bankData.getData();
            log.info("banks {}",ANSI_BLUE+" bank received");
            bankRespository.deleteAll();
            if (bankRespository.findAll().isEmpty()) {
                bankDataList.forEach(data -> {
                    bankRespository.save(new Banks(data.getCode(), data.getName()));
                });
            }
        }catch (Exception e){
            log.info("error {}",ANSI_PURPLE+e.getMessage());
        }
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        runScheduleTask();
}
    @Override
    public FetchAccount getBankCodeAndSend(String bankName, String accountNumber) {
        appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        PaystactAPIUtils.apiDetails(headers);
        headers.setBearerAuth("sk_test_652cb496246c20a1a51456bdf96c12485b37cf7c");
       Banks findName = bankRespository.findBanksByBankName(bankName).orElseThrow(()-> new BankNotFoundException(bankName));
       String url="https://api.paystack.co/bank/resolve";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("account_number",accountNumber)
                .queryParam("bank_code",findName.getBankCode())
                .queryParam("currency","NGN");
        String urlbulder = uriComponentsBuilder.toUriString();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<FetchAccount> responseEntity = restTemplate.exchange(urlbulder, HttpMethod.GET, requestEntity, FetchAccount.class);
        FetchAccount responseBody = responseEntity.getBody();
        log.info("response {}",responseBody);
        return responseBody;


    }

    @Override
    public RequestAccount saveAccount(RequestAccount requestAccount) {
        System.out.println(UserUtils.getAccessTokenEmail());
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        Banks banks = bankRespository.findBanksByBankName(requestAccount.getBankName()).orElseThrow(()->new BankNotFoundException("Bank Not Found"));
        Account account = Account.builder()
                .accountName(requestAccount.getAccountName())
                .bankName(requestAccount.getBankName())
                .accountNumber(requestAccount.getAccountNumber())
                .user(user)
                .build();
        SubAccountRequest request = new SubAccountRequest(
                "CADIOC COMPANY",
                banks.getBankCode(),
                requestAccount.getAccountNumber(),
                "1");
        String subaccount_code = subAccount(headers,request).getData().getSubaccount_code();
        account.setSubaccount_code(subaccount_code);
        return modelMapper.map(accountRespository.save(account),RequestAccount.class);

    }


    private BanksRepo subAccount(HttpHeaders headers, SubAccountRequest requestAccount) {
        String url = "https://api.paystack.co/subaccount";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("business_name", requestAccount.getBusiness_name());
        body.add("bank_code", requestAccount.getBank_code());
        body.add("account_number", requestAccount.getAccount_number());
        body.add("percentage_charge", requestAccount.getPercentage_charge());
        PaystactAPIUtils.apiDetails(headers);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<BanksRepo> response = restTemplate.postForEntity(url, httpEntity, BanksRepo.class);
        log.info("{}",Objects.requireNonNull(response.getBody()).getMessage());
        return response.getBody();

    }

    @Override
    public String verifyCard(BvnRequest bvnRequest) {
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        Account account = accountRespository.findAccountByUser(user).orElseThrow(()->new AccountNotFoundException("ACCOUNT NOT FOUND"));
        Banks banks = bankRespository.findBanksByBankName(account.getBankName())
                .orElseThrow(()-> new BankNotFoundException("Bank Not Found"));
        String customer_number ="CUS_1ndrohmo7ngbjk9";
        String url = "https://api.paystack.co/customer/"+customer_number+"+/identification";
        PaystactAPIUtils.apiDetails(headers);
        RequestObject requestBody = new RequestObject(
                "NG",
                "bank_account",
                account.getAccountNumber(),
                bvnRequest.getBvn(),
                banks.getBankCode(),
                bvnRequest.getFirstName(),
                bvnRequest.getLastName()
        );
        HttpEntity<RequestObject> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, customer_number);
        return responseEntity.getBody();
    }

    @Override
    public AccountResponse updateAccount(AccountRequest accountRequest) {
        PaystactAPIUtils.apiDetails(headers);
        return null;
    }

    @Override
    public AccountResponse deleteAccount(String subaccount_code) {
        return null;
    }

    @Override
    public AccountResponse getAccountByLogedInUser() {
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        Account account =accountRespository.findAccountByUser(user)
                .orElseThrow(()-> new AccountNotFoundException("ACCOUNT NOT FOUND"));
        return AccountResponse.builder()
                .fetchAccount(new FetchAccount(true,"resolve",new Accounts(account.getAccountNumber(),account.getAccountName(),account.getAccountId())))
                .build();
    }


}
