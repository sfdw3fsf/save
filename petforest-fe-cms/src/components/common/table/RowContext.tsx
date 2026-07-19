import type { SyntheticListenerMap } from '@dnd-kit/core/dist/hooks/utilities';
import React from 'react';

interface RowContextProps {
  setActivatorNodeRef?: (element: HTMLElement | null) => void;
  listeners?: SyntheticListenerMap;
}

const RowContext = React.createContext<RowContextProps>({});

export default RowContext;
