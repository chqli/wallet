package com.agrostar.wallet.converter;

import com.agrostar.wallet.dto.WalletResponse;
import com.agrostar.wallet.entity.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converters {
  @Autowired ModelMapper modelMapper;

  public WalletResponse convertToDto(Wallet post) throws WalletNotFoundException {
    if (post.getWalletId() != null) {

      return modelMapper.map(post, WalletResponse.class);
    }
    throw new WalletNotFoundException();
  }
}
