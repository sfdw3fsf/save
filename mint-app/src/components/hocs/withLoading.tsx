import { ComponentType, Suspense } from 'react';
import Loading from '../core/loading/Loading';

type ReturnType<T> = (props: T) => JSX.Element;

export const withLoading = <T extends object>(Component: ComponentType<T>): ReturnType<T> => {
  const WrappedComponent = (props: T) => (
    <Suspense fallback={<Loading />}>
      <Component {...props} />
    </Suspense>
  );

  WrappedComponent.displayName = `WithLoading(${Component.displayName || Component.name || 'Component'})`;

  return WrappedComponent;
};
