import type { ViewProps } from "../../../shared/types/Props";
import { VIEWS } from "../../../shared/types/Constants";
import Button from "../../../shared/components/Button";
import UnorderedList from "../../../shared/components/UnorderedList";

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
