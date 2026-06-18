import type { ButtonHTMLAttributes } from "react";
interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  className?: string;
}
function Button({ className = "", children, ...props }: ButtonProps) {
  const baseClasses =
    "w-full m-1 px-4 py-2 rounded text-white bg-blue-700 text-white hover:bg-blue-800 active:scale-95 active:bg-blue-850 font-medium transition-colors";

  return (
    <button className={`${baseClasses} ${className}`} {...props}>
      {children}
    </button>
  );
}

export default Button;
