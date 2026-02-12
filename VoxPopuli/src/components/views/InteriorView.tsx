import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import Button from "../generics/Button";
import UnorderedList from "../generics/UnorderedList";

export function InteriorView({ changeViewTo }: ViewProps) {
  return (
    <UnorderedList>
      <li>
        <Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</Button>
      </li>
    </UnorderedList>
  );
}
