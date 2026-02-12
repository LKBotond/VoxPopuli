import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import Button from "../generics/Button";
import UnorderedList from "../generics/UnorderedList";

export function IndexView({ changeViewTo }: ViewProps) {
  return (
    <UnorderedList>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.LOGIN)}>Login</Button>
      </li>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.REGISTER)}>Register</Button>
      </li>
    </UnorderedList>
  );
}
