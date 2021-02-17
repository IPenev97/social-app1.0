package com.devproject.usersystem.configuration;

import com.devproject.usersystem.data.models.Profile;
import com.devproject.usersystem.data.models.enums.Gender;
import com.devproject.usersystem.services.models.ProfileServiceModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileDisplayModel;
import com.devproject.usersystem.web.view.models.profiles.ProfileUpdateModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        initMapper(mapper);
    }

    private static void initMapper(ModelMapper mapper) {
        Converter<String, Gender> stringToGenderConverter =
                input -> Gender.valueOf(input.getSource().toUpperCase());

        mapper.createTypeMap(ProfileUpdateModel.class, ProfileServiceModel.class)
                .addMappings(map -> map
                        .using(stringToGenderConverter)
                        .map(
                                ProfileUpdateModel::getGender,
                                ProfileServiceModel::setGender
                        )
                );

    }

    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }

}
