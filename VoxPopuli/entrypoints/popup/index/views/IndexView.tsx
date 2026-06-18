import * as UI from "@/entrypoints/common/components/UI"
import { BaseViewProp } from "@/entrypoints/common/types/Props";
import { VIEWS } from "@/entrypoints/common/types/Props";

interface IndexViewProps extends BaseViewProp{

}

export function IndexView(props: IndexViewProps) {
  return (
      <UI.UL>
        <li>
          <UI.Button onClick={() => props.changeViewTo(VIEWS.LOGIN)}>Login</UI.Button>
        </li>
        <li>
          <UI.Button onClick={() => props.changeViewTo(VIEWS.REGISTER)}>Register</UI.Button>
        </li>
      </UI.UL>

  );
}
