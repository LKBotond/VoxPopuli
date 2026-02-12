import type { InputHTMLAttributes } from "react";
interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  className?: string;

}
function Input({ className = "", ...props }: InputProps) {
  const baseClasses = "w-full m-1 px-4 py-2 rounded text-white, border";

  return (
    <input
      className={`${baseClasses} ${className}`}
      {...props}
    />
  );
}

export default Input;
