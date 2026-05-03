import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import ExteriorApp from './ExteriorApp'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ExteriorApp />
  </StrictMode>,
)
