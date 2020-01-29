package com.youcode.spring.sbootapi.admin.controllers;

import com.youcode.spring.sbootapi.admin.dtos.AdminUserCreateOrEditForm;
import com.youcode.spring.sbootapi.admin.dtos.users.AdminUsersListResponse;
import com.youcode.spring.sbootapi.dtos.response.UserDetailsDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.models.Role;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.RolesService;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import com.youcode.spring.sbootapi.dtos.response.UserDetailsDto;
import com.youcode.spring.sbootapi.dtos.response.base.AppResponse;
import com.youcode.spring.sbootapi.dtos.response.base.ErrorResponse;
import com.youcode.spring.sbootapi.models.Role;
import com.youcode.spring.sbootapi.models.User;
import com.youcode.spring.sbootapi.services.RolesService;
import com.youcode.spring.sbootapi.services.auth.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController(value = "adminUsersController")
@RequestMapping("admin/users")
public class UsersController {

    private final UsersService usersService;

    private RolesService rolesService;

    @Autowired
    public UsersController(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    @GetMapping
    public AdminUsersListResponse index(HttpServletRequest request,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "page_size", defaultValue = "30") int pageSize) {

        Page<User> usersPage = this.usersService.getLatest(page, pageSize);
        return AdminUsersListResponse.build(usersPage, request.getRequestURI());
    }

    @PostMapping
    public UserDetailsDto create(@Valid @RequestBody AdminUserCreateOrEditForm form) {

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());

        HashSet<Role> roles = new HashSet<>(form.getRoles().size());
        user.setRoles(roles);
        form.getRoles().forEach((role) -> {
            user.getRoles().add(rolesService.getOrCreate(role.getName()));
        });

        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());

        this.usersService.createUser(user);
        return UserDetailsDto.build(user);
    }

    @GetMapping("{id}")
    public UserDetailsDto show(@PathVariable("id") Long id) {
        User user = this.usersService.findById(id);
        return UserDetailsDto.build(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<AppResponse> update(@PathVariable("id") Long id,
                                              @Valid @RequestBody AdminUserCreateOrEditForm form,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ErrorResponse(bindingResult.getModel()), HttpStatus.BAD_REQUEST);
        } else {
            User user = usersService.getReference(id);
            user = usersService.updateUser(user, form.getFirstName(), form.getLastName(), form.getUsername(), form.getPassword(), form.getRoles());
            UserDetailsDto dto = UserDetailsDto.build(user);
            dto.setFullMessages(Arrays.asList("Updated successfully"));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        User user = this.usersService.findByIdThrowException(id);
        this.usersService.delete(user);
        return "admin/users/delete";
    }
}
