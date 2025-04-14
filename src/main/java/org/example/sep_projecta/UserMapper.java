package org.example.sep_projecta;

/**
 * Utility class for converting between {@link User} entity objects and {@link UserDTO} data transfer objects.
 * <p>
 * This class is commonly used to separate the domain model from the presentation or transport layer,
 * such as in APIs or UI logic. It provides methods to convert from a {@link User} entity to a {@link UserDTO}
 * and vice versa.
 * </p>
 *
 * <p><b>Note:</b> When converting to a {@link UserDTO}, the password field is excluded for security purposes.
 * Similarly, when converting to a {@link User} entity, it is assumed that the password field has been securely
 * handled (e.g., hashed).</p>
 */
public class UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDTO}.
     * <p>
     * This method copies all fields from the {@link User} entity to the {@link UserDTO},
     * except for the password field, which is intentionally omitted for security reasons.
     * </p>
     *
     * @param user the {@link User} entity to convert (may be {@code null})
     * @return a {@link UserDTO} with copied values, or {@code null} if the input {@code user} is {@code null}
     */
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setTeacher(user.isTeacher());
        dto.setUsername(user.getUsername());
        // Do not set the password for security reasons when converting to DTO.
        return dto;
    }

    /**
     * Converts a {@link UserDTO} to a {@link User} entity.
     * <p>
     * This method copies all fields from the {@link UserDTO} to the {@link User} entity.
     * The password field is included, assuming that it has been securely handled (e.g., hashed) before calling this method.
     * </p>
     *
     * @param dto the {@link UserDTO} to convert (may be {@code null})
     * @return a {@link User} entity with copied values, or {@code null} if the input {@code dto} is {@code null}
     */
    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setTeacher(dto.isTeacher());
        user.setUsername(dto.getUsername());
        // When registering, the password from the DTO may be hashed before setting it.
        user.setPassword(dto.getPassword());
        return user;
    }
}
