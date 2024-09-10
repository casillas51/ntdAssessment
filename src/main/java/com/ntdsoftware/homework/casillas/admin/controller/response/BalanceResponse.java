package com.ntdsoftware.homework.casillas.admin.controller.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Response object for balance-related operations.
 * This class contains the user ID and the balance amount.
 */
@Data
@Accessors(chain = true)
public class BalanceResponse {

    /**
     * The ID of the user.
     */
    private Integer userId;

    /**
     * The balance amount of the user.
     */
    private double balance;
}