import type { ViewProps } from "../../types/Props";
import { VIEWS } from "../../types/Constants";
import * as UI from "../Index";
import { submitRegistrationForm } from "../../api/handlers/HandleSubmission";
import { redirect } from "../../utils/helpers";

export function RegistrationView({ changeViewTo }: ViewProps) {
  const handleRegistration = async (event: React.SubmitEvent) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    if (!(await submitRegistrationForm(formData))) {
      alert("Something went wrong");
      return;
    }
    return redirect("interior.html");
  };

  return (
    <UI.Form onSubmit={handleRegistration}>
      <UI.H>Register</UI.H>
      <UI.Input
        name="email"
        placeholder="email"
        required
        type="text"
      ></UI.Input>
      <UI.Input
        name="alias"
        placeholder="alias"
        required
        type="text"
      ></UI.Input>
      <UI.Input
        name="passArray"
        placeholder="password"
        required
        type="password"
      ></UI.Input>
      <UI.Button type="submit">Register</UI.Button>

      <UI.Button onClick={() => changeViewTo(VIEWS.INDEX)}>back</UI.Button>
    </UI.Form>
  );
}
