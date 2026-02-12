import type { ButtonHTMLAttributes } from "react";
interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  className?: string;
}
function Button({ className = "", children, ...props }: ButtonProps) {
  const baseClasses =
    "w-full m-1 px-4 py-2 rounded text-white bg-blue-400 hover:bg-blue-500 active:scale-95 active:bg-blue-600 font-medium transition-colors";

  return (
    <button className={`${baseClasses} ${className}`} {...props}>
      {children}
    </button>
  );
}

export default Button;
