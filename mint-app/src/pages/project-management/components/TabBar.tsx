import Badge from '@/components/core/badge/Badge';
import { motion } from 'framer-motion';
import { twMerge } from 'tailwind-merge';

export type TabBarItem<K> = {
  label: string;
  value: K;
  badge?: number;
};

type TabBarProps<K> = {
  tabs: TabBarItem<K>[];
  selectedTab: K;
  onChangeTab: (selectedTab: K) => void;
  showBadge?: boolean;
  contClassName?: string;
  itemClassName?: string;
};

const TabBar = <K, _>({
  tabs,
  selectedTab,
  onChangeTab,
  showBadge = false,
  contClassName,
  itemClassName,
}: TabBarProps<K>): JSX.Element => {
  return (
    <div
      className={twMerge(
        'flex flex-row flex-nowrap w-full border-b-[1px] overflow-x-auto scrollbar-none relative',
        contClassName,
      )}>
      {tabs?.map((tab, index) => {
        const isSelected = selectedTab === tab.value;
        return (
          <button
            key={index}
            className={twMerge(
              'py-4 relative flex-shrink-0 flex items-center justify-center',
              index === 0 ? null : 'ml-7',
              itemClassName,
            )}
            onClick={() => onChangeTab(tab.value)}>
            <span className={twMerge(isSelected ? 'text-comp-text-default' : 'text-slate-500')}>{tab.label}</span>

            {showBadge && <Badge label={tab.badge ?? 0} color='neutral' shape='round' className='ml-2' />}

            {isSelected && (
              <motion.div
                layoutId='underline'
                className='absolute bottom-0 left-0 right-0 h-[2px] bg-slate-500'
                transition={{ duration: 0.3 }}
              />
            )}
          </button>
        );
      })}
    </div>
  );
};

export default TabBar;
