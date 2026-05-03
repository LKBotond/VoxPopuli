import type { ViewProps } from "../../../shared/types/Props";
import { VIEWS } from "../../../shared/types/Constants";
import * as UI from "../../../shared/UI";
import { submitRegistrationForm } from "../services/FormService";
import { redirect } from "../../../shared/utils/helpers";

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
