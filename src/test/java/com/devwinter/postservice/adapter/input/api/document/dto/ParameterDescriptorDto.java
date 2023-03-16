package com.devwinter.postservice.adapter.input.api.document.dto;

import com.epages.restdocs.apispec.SimpleType;

public record ParameterDescriptorDto (
        String name,
        SimpleType type,
        String description
) {
    public static ParameterDescriptorDto parameterDescriptor(String name, SimpleType type, String description) {
        return new ParameterDescriptorDto(name,  type, description);
    }

}
