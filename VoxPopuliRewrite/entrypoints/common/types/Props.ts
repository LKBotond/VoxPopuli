export const VIEWS = {
  INDEX: "index",
  LOGIN: "login",
  REGISTER: "register",
  INTERIOR: "interior",
} as const;

export type View = typeof VIEWS[keyof typeof VIEWS];

export interface BaseViewProp {
  changeViewTo: (view: View) => void;
}
