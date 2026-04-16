import type {HTMLAttributes} from "react";

interface SectionProps extends HTMLAttributes<HTMLElement> {
  className?: string;
}

/**
 * Section element with some base classes.
 * @remarks
 * **Important aspects**: width hight full, z-index 50, grid, fixed, inset 0 
 */
function Section({ className = "", ...props }: SectionProps) {
  const baseClasses = "w-full h-full fixed inset-0 bg-gray-900/70 z-50 backdrop-blur-sm grid";

  return <section className={`${baseClasses} ${className}`} {...props} />;
}


export default Section;
