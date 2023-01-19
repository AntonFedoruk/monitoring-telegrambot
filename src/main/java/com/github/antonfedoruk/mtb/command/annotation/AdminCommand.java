package com.github.antonfedoruk.mtb.command.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mark if {@link com.github.antonfedoruk.mtb.command.Command} can be viewed only by admins.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminCommand {
}
