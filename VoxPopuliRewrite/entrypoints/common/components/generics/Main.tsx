import type { FormHTMLAttributes } from "react";
interface FormProps extends FormHTMLAttributes<HTMLFormElement> {
  className?: string;
}
function Main({ className = "", children, ...props }: FormProps) {
  const baseClasses =
    "flex items-center justify-center min-w-[350px] min-h-[400px] h-screen bg-neutral-800 rounded-xl";
  return (
    <main className={`${baseClasses} ${className}`} {...props}>
      {children}
    </main>
  );
}
export default Main;
