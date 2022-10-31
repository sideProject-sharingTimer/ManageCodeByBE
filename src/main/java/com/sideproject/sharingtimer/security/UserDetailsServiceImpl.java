package com.sideproject.sharingtimer.security;

import com.sideproject.sharingtimer.model.User;
import com.sideproject.sharingtimer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsImpl loadUserByUsername(String userPk) throws UsernameNotFoundException {
    User user = userRepository.findById(Long.parseLong(userPk))
            .orElseThrow(() -> new UsernameNotFoundException(userPk + "은 존재하지 않는 아이디입니다."));

    return new UserDetailsImpl(user);
  }
}