import { isEmpty, isNil } from 'lodash';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';
import queryString from 'query-string';
import { useCallback, useMemo } from 'react';

const useRouterHandler = () => {
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const currentParams = useMemo(() => Object.fromEntries(new URLSearchParams(searchParams.toString()).entries()), [searchParams]);
  const createQueryObject = useCallback(
    (name: string, value: string | undefined) => {
      const params = new URLSearchParams(searchParams.toString());
      if (!isNil(value)) {
        params.set(name, value);
      } else {
        params.delete(name);
      }

      return Object.fromEntries(params.entries());
    },
    [searchParams],
  );

  const handleURLPush = (field: string, value: string | undefined, keepQuery = true) => {
    const newQuery = keepQuery ? createQueryObject(field, value) : { [field]: value };
    router.push(`${pathname}?${queryString.stringify(newQuery)}`);
  };

  const handleURLMultiplePush = (params: { [key: string]: string }, keepQuery = true) => {
    const newQuery = new URLSearchParams(searchParams.toString());

    if (keepQuery) {
      Object.entries(currentParams).forEach(([key, value]) => {
        newQuery.set(key, value);
      });
    }
    Object.entries(params).forEach(([key, value]) => {
      if (value && !isEmpty(value)) {
        newQuery.set(key, value);
      } else {
        newQuery.delete(key);
      }
    });

    Object.entries(params).forEach(([key, value]) => {
      if (value && !isEmpty(value)) {
        newQuery.set(key, value);
      } else {
        newQuery.delete(key);
      }
    });

    router.push(`${pathname}?${newQuery.toString()}`);
  };

  return { handleURLPush, createQueryObject, handleURLMultiplePush, currentParams };
};

export default useRouterHandler;
