import screens from '@/utils/theme/screens';
import { useMediaQuery } from 'react-responsive';

const breakpoints = screens;

function capitalize<T extends string>(key: T): Capitalize<T> {
  return (key.charAt(0).toUpperCase() + key.slice(1)) as Capitalize<T>;
}

export function useBreakpoint<T extends keyof typeof breakpoints>(key: T) {
  const bool = useMediaQuery({
    query: `(min-width: ${breakpoints[key]})`,
  });

  type Key = `hasMinWidth${Capitalize<T>}`;

  return {
    [`hasMinWidth${capitalize(key)}`]: bool,
  } as Record<Key, boolean>;
}
