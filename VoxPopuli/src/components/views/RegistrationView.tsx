import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import Button from "../generics/Button";

export function RegistrationView({ changeViewTo }: ViewProps) {
  return (
    <ul>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.INTERIOR)}>Register</Button>
      </li>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</Button>
      </li>
    </ul>
  );
}
