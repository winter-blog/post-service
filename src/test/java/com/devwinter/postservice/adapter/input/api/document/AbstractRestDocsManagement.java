package com.devwinter.postservice.adapter.input.api.document;

import com.devwinter.postservice.adapter.input.api.document.dto.FieldDescriptorDto;
import com.devwinter.postservice.adapter.input.api.document.dto.ParameterDescriptorDto;
import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import com.epages.restdocs.apispec.ParameterDescriptorWithType;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestPartDescriptor;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

public abstract class AbstractRestDocsManagement {
    protected RestDocumentationResultHandler document(
            PostApiDocumentInfo documentInfo,
            Class<?> requestClass,
            Class<?> responseClass,
            List<FieldDescriptorDto> requestFields,
            List<FieldDescriptorDto> responseFields) {

        return MockMvcRestDocumentation.document(
                documentInfo.getIdentifier(),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                        snippet(documentInfo, requestClass, responseClass, requestFields, responseFields, null)
                )
        );
    }

    protected RestDocumentationResultHandler document(
            PostApiDocumentInfo documentInfo,
            Class<?> requestClass,
            Class<?> responseClass,
            List<FieldDescriptorDto> requestFields,
            List<FieldDescriptorDto> responseFields,
            List<ParameterDescriptorDto> parameterFields) {

        return MockMvcRestDocumentation.document(
                documentInfo.getIdentifier(),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                        snippet(documentInfo, requestClass, responseClass, requestFields, responseFields, parameterFields)
                )
        );
    }

    protected RestDocumentationResultHandler document(
            PostApiDocumentInfo documentInfo,
            Class<?> responseClass,
            List<RequestPartDescriptor> requestParts,
            List<FieldDescriptorDto> responseFields) {

        return MockMvcRestDocumentation.document(
                documentInfo.getIdentifier(),
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParts(requestParts),
                resource(
                        snippet(documentInfo, StandardMultipartHttpServletRequest.class, responseClass, null, responseFields, null)
                )
        );
    }

    private ResourceSnippetParameters snippet(PostApiDocumentInfo documentInfo, Class<?> requestClass, Class<?> responseClass, List<FieldDescriptorDto> requestFields, List<FieldDescriptorDto> responseFields,
                                              List<ParameterDescriptorDto> parameterDescriptors) {

        return ResourceSnippetParameters.builder()
                                        .tag(documentInfo.getTag())
                                        .summary(documentInfo.getSummary())
                                        .description(documentInfo.getDescription())
                                        .requestHeaders(
                                                (documentInfo.isCertification()) ?
                                                        List.of(new HeaderDescriptorWithType("MemberId").description("회원 Id"))
                                                        : Collections.emptyList()
                                        )
                                        .requestSchema((requestClass != null) ? schema(requestClass.getName()) : null)
                                        .responseSchema((responseClass != null) ? schema(responseClass.getName()) : null)
                                        .requestFields((requestFields != null) ? fieldConverter(requestFields) : Collections.emptyList())
                                        .responseFields((responseFields != null) ? fieldConverter(responseFields) : Collections.emptyList())
                                        .pathParameters((parameterDescriptors != null) ? parameterConverter(parameterDescriptors) : Collections.emptyList())
                                        .build();
    }

    private List<FieldDescriptor> fieldConverter(List<FieldDescriptorDto> fieldDescriptors) {
        return fieldDescriptors.stream()
                               .map(field ->
                                       fieldWithPath(field.path())
                                               .type(field.fieldType())
                                               .description(field.description()))
                               .collect(Collectors.toList());
    }

    private List<ParameterDescriptorWithType> parameterConverter(List<ParameterDescriptorDto> parameterDescriptors) {
        return parameterDescriptors.stream()
                                   .map(field -> new ParameterDescriptorWithType(field.name()).type(field.type()).description(field.description()))
                                   .collect(Collectors.toList());
    }


    private ConstraintDescriptions getConstraintDescriptions(Class<?> cls) {
        ResourceBundleConstraintDescriptionResolver fallback = new ResourceBundleConstraintDescriptionResolver();
        return new ConstraintDescriptions(cls, (constraint) -> {
            String message = (String) constraint.getConfiguration()
                                                .get("message");
            if (message != null) {
                return message;
            }
            return fallback.resolveDescription(constraint);
        });
    }
}
