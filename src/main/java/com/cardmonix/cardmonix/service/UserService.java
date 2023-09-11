package com.cardmonix.cardmonix.service;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.request.UserRequestDTO;
import com.cardmonix.cardmonix.response.CoinResponse;
import com.cardmonix.cardmonix.response.ResponseFromUser;
import com.cardmonix.cardmonix.response.UserResponse;
import com.cardmonix.cardmonix.response.WalletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface UserService {
    List<User> userBirthday(String date);
    UserResponse view();
    List<WalletResponse> getWallet();

    ResponseFromUser upateUserById(UserRequestDTO userRequestDTO);

    String updatePicture(MultipartFile multipartFile);
    CoinResponse getCoinByName(String name);
}
