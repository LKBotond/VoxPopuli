import { handleLogout } from "../../../api/handlers/HandleSubmission";
import { redirect } from "../../../shared/utils/helpers";
import * as UI from "../../../shared/UI";

export function InteriorView() {
  const logout = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    if (!(await handleLogout())) {
      alert("Something went wrong");
      return;
    }
    return redirect("index.html");
  };
  return (
    <UI.UL className="text-center">
      <li>
        <UI.H className="mb-2">You are in</UI.H>
      </li>
      <li>
       <UI.Button onClick={logout}>logout</UI.Button>
      </li>
    </UI.UL>
  );
}
