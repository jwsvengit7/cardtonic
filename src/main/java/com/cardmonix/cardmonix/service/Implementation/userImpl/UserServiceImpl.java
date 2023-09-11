package com.cardmonix.cardmonix.service.Implementation.userImpl;

import com.cardmonix.cardmonix.configurations.CloudinaryConfig;
import com.cardmonix.cardmonix.domain.entity.coins.Coin;
import com.cardmonix.cardmonix.domain.entity.userModel.CoinWallet;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.domain.repository.CoinRepository;
import com.cardmonix.cardmonix.domain.repository.CoinWalletRepository;
import com.cardmonix.cardmonix.domain.repository.UserRepository;
import com.cardmonix.cardmonix.eceptions.CoinNotFoundException;
import com.cardmonix.cardmonix.eceptions.UserNotFoundException;
import com.cardmonix.cardmonix.request.UserRequestDTO;
import com.cardmonix.cardmonix.response.CoinResponse;
import com.cardmonix.cardmonix.response.ResponseFromUser;
import com.cardmonix.cardmonix.response.UserResponse;
import com.cardmonix.cardmonix.response.WalletResponse;
import com.cardmonix.cardmonix.service.Implementation.authImpl.AuthServiceImpl;
import com.cardmonix.cardmonix.service.UserService;
import com.cardmonix.cardmonix.utils.DateUtils;
import com.cardmonix.cardmonix.utils.RandomValues;
import com.cardmonix.cardmonix.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthServiceImpl authServiceImplentation;
    private final CoinRepository coinRepository;
    private final CoinWalletRepository coinWalletRepository;
    @Override
    public UserResponse view(){
        User user = finduser(UserUtils.getAccessTokenEmail());
        System.out.println(user);
        System.out.println("hello*********");
        return modelMapper.map(user,UserResponse.class);
    }

    @Override
    public List<WalletResponse> getWallet() {
        User user = finduser(UserUtils.getAccessTokenEmail());
        List<CoinWallet> coinWallets = coinWalletRepository.findCoinWalletByUser(user);
        return coinWallets.stream().map((coinWallet -> modelMapper.map(coinWallet,WalletResponse.class))).collect(Collectors.toList());
    }


    private User finduser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("USER NOT FOUND"));
        return user;
    }


    @Override
    public List<User> userBirthday(String date) {
        return userRepository.findUserByDob(date);
    }


    @Override
    public ResponseFromUser upateUserById(UserRequestDTO userRequestDTO) {
        User user = authServiceImplentation.verifyUser(UserUtils.getAccessTokenEmail());
            user.setUser_name(userRequestDTO.getUser_name());
            user.setDob(DateUtils.getDate(userRequestDTO.getDob()));
            user.setPhone(userRequestDTO.getPhone());
           return modelMapper.map(userRepository.save(user),ResponseFromUser.class);
    }

    @Override
    public String updatePicture(MultipartFile multipartFile) {
        User user = authServiceImplentation.verifyUser(UserUtils.getAccessTokenEmail());
        CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
        String link = cloudinaryConfig.uploadPicture(multipartFile,RandomValues.generateRandom());
        user.setProfile(link);
        userRepository.save(user);
        return link;
    }

    @Override
    public CoinResponse getCoinByName(String name) {
        Coin coin = coinRepository.findByName(name).orElseThrow(()->new CoinNotFoundException("Coin not found"));
        return modelMapper.map(coin,CoinResponse.class);
    }

}
