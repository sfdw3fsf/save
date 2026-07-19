import useResize from '@/hook/useResize';

type ListDataProps<T> = {
  data: T[];
  renderItem: (item: T, index: number) => React.ReactNode;
  keyExtractor: (item: T) => string | number;
  styleContainer?: string;
  styleList?: string;
  numberOfColumns?: number;
  gap?: number;
  isAlways?: boolean;
  noWrap?: boolean;
};

export const ListData = <T,>({
  data,
  renderItem,
  styleContainer,
  styleList,
  numberOfColumns,
  gap = 16,
  keyExtractor,
  isAlways = false,
  noWrap = false,
}: ListDataProps<T>) => {
  const { isMobile, isTablet } = useResize();
  const itemWidthPercent =
    isAlways && numberOfColumns
      ? 100 / numberOfColumns
      : isMobile && !noWrap
        ? 100
        : isTablet && !noWrap
          ? 50
          : numberOfColumns
            ? 100 / numberOfColumns
            : undefined;
  const itemGap = numberOfColumns && gap ? (gap * (numberOfColumns - 1)) / numberOfColumns : undefined;

  return (
    data &&
    data.length > 0 &&
    (numberOfColumns ? (
      <div className={styleContainer} style={{ alignItems: 'center', justifyContent: 'center' }}>
        <ul className={`flex w-full flex-wrap ${styleList}`} style={{ gap: `${gap}px` }}>
          {data.map((item, index: number) => (
            <li
              key={keyExtractor(item)}
              style={{ width: `calc(${itemWidthPercent}% - ${itemWidthPercent === 100 ? 0 : itemGap}px)` }}>
              {renderItem(item, index)}
            </li>
          ))}
        </ul>
      </div>
    ) : (
      <div className={styleContainer}>
        <ul className={`flex w-full flex-wrap justify-center gap-3 ${styleList}`}>
          {data.map((item, index: number) => (
            <li key={keyExtractor(item)}>{renderItem(item, index)}</li>
          ))}
        </ul>
      </div>
    ))
  );
};
