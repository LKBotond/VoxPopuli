import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";

export function IndexView({ changeViewTo }: ViewProps) {
  return (
    <ul>
      <li>
        <button onClick={() => changeViewTo(VIEWS.LOGIN)}>Login</button>
      </li>
      <li>
        <button onClick={() => changeViewTo(VIEWS.REGISTER)}>Register</button>
      </li>
    </ul>
  );
}
