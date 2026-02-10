import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import Button from "../generics/Button";

export function IndexView({ changeViewTo }: ViewProps) {
  return (
    <ul>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.LOGIN)}>Login</Button>
      </li>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.REGISTER)}>Register</Button>
      </li>
    </ul>
  );
}
