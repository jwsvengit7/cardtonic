package com.cardmonix.cardmonix.controllers;

import com.cardmonix.cardmonix.request.UserRequestDTO;
import com.cardmonix.cardmonix.response.*;
import com.cardmonix.cardmonix.service.SearchService;
import com.cardmonix.cardmonix.service.UserService;
import com.cardmonix.cardmonix.utils.PageConstantReqUtils;
import com.cardmonix.cardmonix.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SearchService service;


    @CrossOrigin("*")
    @PutMapping(value = "/upload-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadPicture(@RequestParam("file") MultipartFile file){
        ApiResponse<String> apiResponse = new ApiResponse<>(userService.updatePicture(file));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping( "/user-details")
    public ResponseEntity<ApiResponse<UserResponse>> viewDetails(){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(userService.view());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping( "/getcoinByName")
    public ResponseEntity<ApiResponse<CoinResponse>> getCoin(@RequestParam("coin") String coin){
        ApiResponse<CoinResponse> apiResponse = new ApiResponse<>(userService.getCoinByName(coin));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/veiw-coin")
    public ResponseEntity<ApiResponse<PaginationUtils>> view_coin(
            @RequestParam(value = "pageNo", defaultValue = PageConstantReqUtils.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstantReqUtils.pageSize) Integer pageSize
    ){
        ApiResponse<PaginationUtils> apiResponse = new ApiResponse<>(service.viewCoin(pageNo,pageSize));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @CrossOrigin("*")
    @GetMapping("/wallets")
    public ResponseEntity<ApiResponse<List<WalletResponse>>> wallets(
    ){
        ApiResponse<List<WalletResponse>> apiResponse = new ApiResponse<>(userService.getWallet());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @CrossOrigin("*")
    @GetMapping("/search-coin")

    public ResponseEntity<ApiResponse<PaginationUtils>> searchCoin(
            @RequestParam(value = "pageNo", defaultValue = PageConstantReqUtils.pageNo) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstantReqUtils.pageSize) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstantReqUtils.sortBy) String sortBy,
            @RequestParam(value= "sortDir",defaultValue = PageConstantReqUtils.sortDir) String sortDir,
            @RequestParam("searchCoinName") String searchCoinName)

    {
        ApiResponse<PaginationUtils> apiResponse = new ApiResponse<>(service.searchByCoin(pageNo,sortBy,sortDir,pageSize,searchCoinName));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @CrossOrigin("*")
    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<ResponseFromUser>> updateDetails(@RequestBody UserRequestDTO userRequestDTO){
        ApiResponse<ResponseFromUser> apiResponse = new ApiResponse<>(userService.upateUserById(userRequestDTO));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}
