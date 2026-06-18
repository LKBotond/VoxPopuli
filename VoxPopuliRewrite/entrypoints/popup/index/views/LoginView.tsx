import { BaseViewProp } from "@/entrypoints/common/types/Props";
import * as UI from "@/entrypoints/common/components/UI"
import { VIEWS } from "@/entrypoints/common/types/Props";
import { redirect } from "@/entrypoints/common/utils/helpers";
import { submitLoginForm } from "../forms/FormService";
interface LoginViewProps extends BaseViewProp{}

export function LoginView(props: LoginViewProps) {
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
        <UI.Button onClick={() => props.changeViewTo(VIEWS.INDEX)}>back</UI.Button>
      </UI.Form>
    </>
  );
}
