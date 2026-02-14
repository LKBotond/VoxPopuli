import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import * as UI from "../Index";

export function InteriorView({ changeViewTo }: ViewProps) {
  return (
    <UI.UL className="text-center">
      <li>
        <UI.H className="mb-2">You are in</UI.H>
      </li>
      <li>
        <UI.Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</UI.Button>
      </li>
    </UI.UL>
  );
}
