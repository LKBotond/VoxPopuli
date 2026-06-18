import { BaseViewProp } from "@/entrypoints/common/types/Props";
import * as UI from "@/entrypoints/common/components/UI";
import { VIEWS } from "@/entrypoints/common/types/Props";
import { redirect } from "@/entrypoints/common/utils/helpers";
import { submitRegistrationForm } from "../forms/FormService";
interface RegistrationViewProps extends BaseViewProp {}

export function RegistrationView(props: RegistrationViewProps) {
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

      <UI.Button onClick={() => props.changeViewTo(VIEWS.INDEX)}>
        back
      </UI.Button>
    </UI.Form>
  );
}
