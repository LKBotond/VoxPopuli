import type { FormHTMLAttributes } from "react";
interface FormProps extends FormHTMLAttributes<HTMLFormElement> {
  className?: string;
}
function Form({ className = "", children, ...props }: FormProps) {
  const baseClasses =
    "flex w-full flex-col gap-2 m-2 p-4 border border-gray-100/50 backdrop-blur-sm rounded-md shadow-sm bg-slate-800";
  return (
    <form className={`${baseClasses} ${className}`} {...props}>
      {children}
    </form>
  );
}
export default Form;
