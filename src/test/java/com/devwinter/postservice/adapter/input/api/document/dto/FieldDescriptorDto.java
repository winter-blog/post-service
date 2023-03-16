package com.devwinter.postservice.adapter.input.api.document.dto;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;

public record FieldDescriptorDto (
            String path,
            JsonFieldType fieldType,
            String description,
            Attributes.Attribute attributes) {
        public static FieldDescriptorDto fieldDescriptor(String path, JsonFieldType fieldType, String description) {
            return new FieldDescriptorDto(path, fieldType, description, null);
        }

        public static FieldDescriptorDto fieldDescriptor(String path, JsonFieldType fieldType, String description, Attributes.Attribute attributes) {
            return new FieldDescriptorDto(path, fieldType, description, attributes);
        }
    }