import type { FormHTMLAttributes } from "react";
interface FormProps extends FormHTMLAttributes<HTMLFormElement> {
  className?: string;
}
function Main({ className = "", children, ...props }: FormProps) {
  const baseClasses =
    "flex items-center justify-center w-full h-screen bg-neutral-800";
  return (
    <form className={`${baseClasses} ${className}`} {...props}>
      {children}
    </form>
  );
}
export default Main;
