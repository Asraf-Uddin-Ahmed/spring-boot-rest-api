package com.asraf.constants;

public final class PreAuthorizeConditions {

    private PreAuthorizeConditions() {
    }

    public static final class Role {
        public static final String USER = "hasRole('USER')";
        public static final String ADMIN = "hasRole('ADMIN')";

        private Role() {
        }
    }

    public static final class Permission {
        public static final String SHOW_WHO_AM_I_TRUE__SHOW_MY_PRINCIPAL_TRUE = "hasPermission('show-who-am-i,show-my-principal', 'true,true')";

        private Permission() {
        }
    }
}
