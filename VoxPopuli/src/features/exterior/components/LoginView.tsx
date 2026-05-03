import type { ViewProps } from "../../../shared/types/Props";
import { VIEWS } from "../../../shared/types/Constants";
import * as UI from "../../../shared/UI";
import { submitLoginForm } from "../../../api/handlers/HandleSubmission";
import { redirect } from "../../../shared/utils/helpers";

export function LoginView({ changeViewTo }: ViewProps) {
  const hanldeLogin = async (event: React.SubmitEvent) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    if (!(await submitLoginForm(formData))) {
      alert("Something went wrong");
      return;
    }
    return redirect("interior.html");
  };

  return (
    <>
      <UI.Form className="text-center" onSubmit={hanldeLogin}>
        <UI.H>Login Form</UI.H>
        <UI.Input
          name="email"
          placeholder="email"
          required
          type="text"
        ></UI.Input>
        <UI.Input
          name="pass"
          placeholder="password"
          required
          type="password"
        ></UI.Input>
        <UI.Button type="submit">Login</UI.Button>
        <UI.Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</UI.Button>
      </UI.Form>
    </>
  );
}
