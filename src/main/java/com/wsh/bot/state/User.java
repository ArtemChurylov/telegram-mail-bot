package com.wsh.bot.state;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    UserState state;
    String targetEmail;
    String emailContent;
}
