package com.example.yonseitalk.web.user.service;

import com.example.yonseitalk.AES128;
import com.example.yonseitalk.exception.DuplicateAccountException;
import com.example.yonseitalk.util.login.role.Role;
import com.example.yonseitalk.web.user.dto.FriendUser;
import com.example.yonseitalk.web.user.dto.SearchUser;
import com.example.yonseitalk.web.user.dao.User;
import com.example.yonseitalk.web.user.dao.UserRepository;
import com.example.yonseitalk.web.user.dto.UserDto;
import com.example.yonseitalk.web.user.dto.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(UserDto userDto){
        userRepository.save(userDto.toUser());
    }

    @Override
    @Transactional
    public UserDto save(UserRegisterRequest userRegisterRequest) {

        userRepository.findById(userRegisterRequest.getUserId())
                .ifPresent(user -> {throw new DuplicateAccountException();});
        userRegisterRequest.setPassword(AES128.getAES128_Encode(userRegisterRequest.getPassword()));
        User user = userRegisterRequest.toEntity();
        user.setConnectionStatus(false);
        user.setStatusMessage("");
        user.setUserLocation("공학관");
        user.setRole(Role.USER.getValue());
        userRepository.save(user);
        return UserDto.fromUser(user);
    }

    @Transactional
    public Optional<UserDto> findById(String id){
        User user = userRepository.findById(id).orElse(null);
        return user==null?Optional.empty():Optional.of(UserDto.fromUser(user));
    }

    @Transactional
    public void deleteById(String id){
        userRepository.deleteById(id);
    }

    @Transactional
    public List<UserDto> findByLocation(String location){
        return userRepository.findByLocation(location).stream().map(UserDto::fromUser).collect(Collectors.toList());
    }

    @Transactional
    public int updateStatusMessage(String id, String msg){
        Optional<User> userOptional = userRepository.findById(id);

        userOptional.ifPresent(user -> user.setStatusMessage(msg));

        return userOptional.isPresent()?1:0;
    }
    @Transactional
    public int updateUserConnectionStatus(String id, Boolean flag){
        Optional<User> userOptional = userRepository.findById(id);

        userOptional.ifPresent(user -> user.setConnectionStatus(flag));

        return userOptional.isPresent()?1:0;
    }

    @Transactional
    public int updateUserLocation(String id, String location){
        Optional<User> userOptional = userRepository.findById(id);

        userOptional.ifPresent(user -> user.setUserLocation(location));

        return userOptional.isPresent()?1:0;
    }

    @Transactional
    public List<FriendUser> findFriendUser(String userId){
        return userRepository.findAll(userId).stream().map(FriendUser::fromProjection).collect(Collectors.toList());
    }

    @Transactional
    public void addFriend(String userId, String friendId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("No user with id "+friendId));
        user.getUserAddedFriends().add(friend);
        friend.getFriendsAddedUser().add(user);
    }

    @Transactional
    public void delFriend(String userId, String friendId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("No user with id "+friendId));
        user.getUserAddedFriends().remove(friend);
        friend.getFriendsAddedUser().remove(user);
    }

    @Transactional
    public boolean isFriend(String userId, String friendId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user with id "+userId));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("No user with id "+friendId));
        return user.getUserAddedFriends().contains(friend);
    }

    @Transactional
    public List<SearchUser> search(String userId, String searchQuery){
        return userRepository.search(userId, searchQuery).stream().map(SearchUser::fromProjection).collect(Collectors.toList());
    }

}
