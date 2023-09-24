package com.cardmonix.cardmonix.service.Implementation.accountImpl;

import com.cardmonix.cardmonix.domain.entity.account.Account;
import com.cardmonix.cardmonix.domain.entity.account.Banks;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.AccountRespository;
import com.cardmonix.cardmonix.domain.repository.BankRespository;
import com.cardmonix.cardmonix.eceptions.AccountIsuesException;
import com.cardmonix.cardmonix.eceptions.AccountNotFoundException;
import com.cardmonix.cardmonix.eceptions.BankNotFoundException;
import com.cardmonix.cardmonix.eceptions.UserNotFoundException;
import com.cardmonix.cardmonix.request.*;
import com.cardmonix.cardmonix.response.*;
import com.cardmonix.cardmonix.service.AccountService;
import com.cardmonix.cardmonix.service.Implementation.authImpl.AuthServiceImpl;
import com.cardmonix.cardmonix.utils.PaystactAPIUtils;
import com.cardmonix.cardmonix.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import static com.cardmonix.cardmonix.utils.PaystactAPIUtils.*;
import static com.cardmonix.cardmonix.utils.app.AppUtils.APP_NAME;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements ApplicationRunner,  AccountService {
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;
    private final BankRespository bankRespository;
    private final PaystactAPIUtils paystactAPIUtils;
    private final AccountRespository accountRespository;
    private final AuthServiceImpl appUserService;
    private final ObjectMapper objectMapper;



    @Scheduled(cron = "0 0 8 * * ?")
    private void runScheduleTask(){
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cache-Control", "no-cache");
        RequestEntity<?>  requestEntity = new RequestEntity<>(headers,HttpMethod.GET, URI.create(PAYSTACK_BANK_API));
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
       User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        HttpHeaders headers1=getHeadersGen(paystactAPIUtils.getKEY(),MediaType.APPLICATION_JSON,"no-cache",headers);
       Banks findName = bankRespository.findBanksByBankName(bankName).orElseThrow(()-> new BankNotFoundException(bankName));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(PAYSTACK_RESOLVE_URL)
                .queryParam("account_number",accountNumber)
                .queryParam("bank_code",findName.getBankCode())
                .queryParam("currency","NGN");
        String urlbulder = uriComponentsBuilder.toUriString();
        HttpEntity<?> requestEntity = new HttpEntity<>(headers1);
        ResponseEntity<FetchAccount> responseEntity = restTemplate.exchange(urlbulder, HttpMethod.GET, requestEntity, FetchAccount.class);
        FetchAccount responseBody = responseEntity.getBody();
        log.info("response {}",responseBody);
        return responseBody;


    }

    @Override
    public String saveAccount(RequestAccount requestAccount) {
        log.info("LOGIN USER{} ",UserUtils.getAccessTokenEmail());
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        if(user.getAccount().size()>0){
            throw new AccountIsuesException("User Already Have an ACCOUNT");
        }
        Banks banks = bankRespository.findBanksByBankName(requestAccount.getBankName()).orElseThrow(()->new BankNotFoundException("Bank Not Found"));
        Account account = Account.builder()
                .accountName(requestAccount.getAccountName())
                .bankName(requestAccount.getBankName())
                .accountNumber(requestAccount.getAccountNumber())
                .user(user)
                .build();
        SubAccountRequest request = new SubAccountRequest(
                APP_NAME,
                banks.getBankCode(),
                requestAccount.getAccountNumber(),
                PERCENTAGE_CHARGE_BY_PAYSTACK);
        String  SUB_ACCOUNT = subAccount(headers,request).getData().getSubaccount_code();
        account.setSubaccount_code(SUB_ACCOUNT);
        accountRespository.save(account);
        return "Account Have Been Saved";

    }


    private BanksRepo subAccount(HttpHeaders headers, SubAccountRequest requestAccount) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("business_name", requestAccount.getBusiness_name());
        body.add("bank_code", requestAccount.getBank_code());
        body.add("account_number", requestAccount.getAccount_number());
        body.add("percentage_charge", requestAccount.getPercentage_charge());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, getHeadersGen(paystactAPIUtils.getKEY(),MediaType.APPLICATION_FORM_URLENCODED,"no-cache",headers));
        ResponseEntity<BanksRepo> response = restTemplate.postForEntity(PAYSTACK_SUBACCOUNT_URL, httpEntity, BanksRepo.class);
        log.info("{}",Objects.requireNonNull(response.getBody()).getMessage());
        return response.getBody();

    }

    @Override
    public String updateAccount(AccountRequestDTO accountRequest) {
        getHeadersGen(paystactAPIUtils.getKEY(),MediaType.APPLICATION_JSON,"no-cache",headers);
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        Account account =accountRespository.findAccountByUser(user)
                .orElseThrow(()->new AccountNotFoundException("Account NOT FOUND"));
        String url =PAYSTACK_SUBACCOUNT_URL+"/"+account.getSubaccount_code();
        Banks banks = bankRespository.findBanksByBankName(accountRequest.getSettlement_bank())
                .orElseThrow(()-> new BankNotFoundException("BANK NOT FOUND"));
        System.out.println(accountRequest.getAccount_number());

        AccountRequest accountRequest1 = new AccountRequest();
        accountRequest1.setAccount_number(accountRequest.getAccount_number());
        accountRequest1.setSettlement_bank(banks.getBankName());
        HttpEntity<AccountRequest> httpEntity = new HttpEntity<>(accountRequest1,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            account.setAccountName(accountRequest.getAccount_name());
            account.setAccountNumber(accountRequest.getAccount_number());
            account.setBankName(accountRequest.getSettlement_bank());
            accountRespository.save(account);

            log.info("{}",responseEntity.getBody());
            return responseEntity.getBody();

        } else {
            throw new AccountIsuesException("Failed to update subaccount: " + httpEntity.getBody());
        }

    }

    @Override
    public Void deleteAccount(String subaccount_code) {
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        String url =PAYSTACK_SUBACCOUNT_URL+"/"+subaccount_code;
        headers.setBearerAuth(paystactAPIUtils.getKEY());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new AccountIsuesException("Failed to delete subaccount");
        }

        accountRespository.deleteByUser(user);
        return responseEntity.getBody();
    }
    @Override
    public Accounts getAccountByLogedInUser(Long id) {
        User user = appUserService.verifyUser(UserUtils.getAccessTokenEmail());
        User findUser = appUserService.findById(id);
        System.out.println(findUser);
        if(!findUser.equals(user))
            throw new UserNotFoundException("USER NOT FOUND");
        else{
            System.out.println(findUser);
            Account account = accountRespository.findAccountByUser(findUser)
                    .orElseThrow(() -> new AccountNotFoundException("ACCOUNT NOT FOUND"));
            return mapAccounts(account);
        }
    }
    private Accounts mapAccounts(Account account){
       return  Accounts.builder()
                .account_name(account.getAccountName())
                .account_number(account.getAccountNumber())
               .bankName(account.getBankName())
                .build();
    }

    private HttpHeaders getHeadersGen(String KEY, MediaType TYPE,String CACHE,HttpHeaders headers){
        headers.setBearerAuth(KEY);
        headers.setContentType(TYPE);
        headers.set("Cache-Control",CACHE);
        return headers;
    }
}
