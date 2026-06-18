import {
  SESSION_HEADER,
  EXTENSION_ID,
  CONTENT_TYPE,
} from "./NamingConventions";
export interface ApiRequest<Theader, Tpayload = undefined> {
  headers: Theader;
  payload?: Tpayload;
}
export interface AuthHeader {
  [CONTENT_TYPE]: string;
  [SESSION_HEADER]: string;
  [EXTENSION_ID]: string;
}
export interface OriginHeader {
  [CONTENT_TYPE]: string;
  [EXTENSION_ID]: string;
}
