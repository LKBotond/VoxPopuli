import type { FormHTMLAttributes } from "react";
interface FormProps extends FormHTMLAttributes<HTMLFormElement> {
  className?: string;
}
function Form({ className = "", children, ...props }: FormProps) {
  const baseClasses =
    "flex w-full flex-col gap-2 p-4 rounded-md shadow-sm ";
  return (
    <form className={`${baseClasses} ${className}`} {...props}>
      {children}
    </form>
  );
}
export default Form;
