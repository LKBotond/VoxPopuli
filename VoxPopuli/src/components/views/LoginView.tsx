import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";

export function LoginView({ changeViewTo }: ViewProps) {
  return (
    <ul>
      <li>
        <button onClick={() => changeViewTo(VIEWS.INTERIOR)}>Login</button>
      </li>
      <li>
        <button onClick={() => changeViewTo(VIEWS.INDEX)}>back</button>
      </li>
    </ul>
  );
}

