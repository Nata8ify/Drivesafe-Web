/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.g40.utils;

import com.senior.g40.service.UserService;

/**
 *
 * @author PNattawut
 */
public class Area51 {
    // For test a thing.
    public static void main(String[] args) {
        UserService.getInstance().login("root", "root", 'T');
    }
}
