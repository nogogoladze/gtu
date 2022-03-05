package com.spring.controller;

import com.spring.email.EmailSender;
import com.spring.exception.AppException;
import com.spring.service.RoleService;
import com.spring.service.SystemUserService;
import com.spring.utils.Utils;
import com.spring.enums.AppErrorCode;
import com.spring.model.system.SystemUser;
import com.spring.model.system.UserRole;
import com.spring.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("app/v1/")
public class SystemUserController {

    private final ServerService serverService;

    private final SystemUserService systemUserService;

    private final RoleService roleService;

    private final EmailSender emailSender;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //ყველა იუზერის ჩამონათვალი
    @GetMapping("system/user")
    public List<SystemUser> getAllUser() {
        return systemUserService.getUsers();
    }

    //იუზერის რეგისტრაცია
    @PostMapping("/system/user/save")
    public SystemUser saveUser(@RequestBody SystemUser systemUser) throws Exception {
        try {
            boolean emailExist = systemUserService.findByEmail(systemUser.getEmail());
            if (emailExist) {
                throw new Exception("email exist");
            }
            String encodePassword = bCryptPasswordEncoder.encode(systemUser.getPassword());
            systemUser.setPassword(encodePassword);
            systemUser.setActive(false);
            String randomNumber = Utils.getRandomNumber();

            systemUser.setRandomNumber(randomNumber);

            emailSender.send(systemUser.getEmail(), randomNumber);
        } catch (AppException exception) {
            throw new AppException(AppErrorCode.SAVE_USER_EXCEPTION);
        }

        return systemUserService.saveSystemUser(systemUser);
    }

    //მომხმარებლის გააქტიურება 4 ნიშნა რიცხვის საშუალებით
    @GetMapping("system/user/active/{number}")
    public void activeSystemUser(@PathVariable String number) {
        systemUserService.findUserByNumber(number);
    }

    //როლის შენახვა
    @PostMapping("/system/role/save")
    public UserRole saveRole(@RequestBody UserRole userRole) {
        return roleService.saveUserRole(userRole);
    }

    //მომხმარებლის წაშლა ID ით
    @DeleteMapping("system/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        systemUserService.delete(id);
    }

    //როლის წაშლა ID ით
    @DeleteMapping("system/role/delete/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.delete(id);
    }

    //როლის გასეტვა იუზერზე
    @PostMapping("system/role/relates/{username}/{rolename}")
    public void relateUser(@PathVariable String username,
                           @PathVariable String rolename) throws AppException {
        systemUserService.roleSetter(username, rolename);
    }

    //იუზერის დეაქტივაცია
    @PostMapping("system/deactivation/{id}")
    public void deactivationUser(@PathVariable int id) {
        systemUserService.deactivationUser(id);
    }

    //იუზერის გააქტიურება
    @PostMapping("system/activation/{id}")
    public void activationUser(@PathVariable int id) {
        systemUserService.activationUser(id);
    }

    //როლის მოშორება იუზერზე
    @PostMapping("system/role/removeRole/{id}")
    public void removeRole(@PathVariable int id) {
        systemUserService.removeRole(id);
    }

    //სერვერის გასეტვა იუზერზე
    @PostMapping("system/role/relate/{username}/{servername}")
    public void relateServer(@PathVariable String username,
                           @PathVariable String servername) throws AppException {
        serverService.relateUser(username, servername);
    }

    //სერვერის მოშორება იუზერზე
    @PostMapping("system/user/remove/{username}/{servername}")
    public void removeServer(@PathVariable String username,
                             @PathVariable String servername) throws AppException {
        serverService.removeServer(username, servername);
    }
}
