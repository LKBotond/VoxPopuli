/**These are the views which the Extension supports in the popup */
export const VIEWS = {
  INDEX: "index",
  LOGIN: "login",
  REGISTER: "register",
  INTERIOR: "interior",
} as const;

/**
 * These are the message actions used by the extension, add here any extra that comes along
 */
export const LOGIN = "login";
export const LOGOUT = "logout";
export const REGISTER = "register";
export const COMMENT = "comment";
export const EDIT = "edit";
export const GET_COMMENTS = "getComments";
export const DELETE_COMMENT = "deleteComment";
export const GET_ALIAS = "getAlias";
