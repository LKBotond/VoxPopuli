import { SESSION_HEADER, EXTENSION_ID } from "./NamingConventions";
export interface ApiRequest<Theader,Tpayload> {
  headers: Theader;
  payload: Tpayload;
}
export interface AuthHeader {
  [SESSION_HEADER]: string;
  [EXTENSION_ID]: string;
}
export interface OriginHeader {
  [EXTENSION_ID]: string;
}
