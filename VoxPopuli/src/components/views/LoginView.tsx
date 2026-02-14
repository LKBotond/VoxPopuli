import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import * as UI from "../Index";

export function LoginView({ changeViewTo }: ViewProps) {
  return (
    <>
      <UI.Form className="text-center">
        <UI.H>Login</UI.H>
        <UI.Input placeholder="email" required type="text"></UI.Input>
        <UI.Input placeholder="password" required type="password"></UI.Input>
        <UI.Button onClick={() => changeViewTo(VIEWS.INTERIOR)}>
          Login
        </UI.Button>
        <UI.Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</UI.Button>
      </UI.Form>
    </>
  );
}
