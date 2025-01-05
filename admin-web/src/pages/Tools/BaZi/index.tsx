          <div className="space-y-4">
            <div className="flex items-center">
              <Text className="text-white/60 w-16">八字：</Text>
              <Text className="text-white/85">
                {result ? `${result.yearGanZhi} ${result.monthGanZhi} ${result.dayGanZhi} ${result.timeGanZhi}` : '待计算'}
              </Text>
            </div>
            <div className="flex items-center">
              <Text className="text-white/60 w-16">五行：</Text>
              {result && (
                <div className="flex items-center gap-2">
                  {wuxingOrder.map((wuxing) => (
                    <Tag
                      key={wuxing}
                      color={wuxingColors[wuxing]}
                      style={{
                        borderColor: wuxingColors[wuxing],
                        backgroundColor: `${wuxingColors[wuxing]}1A`,
                        margin: 0,
                      }}
                    >
                      {`${wuxing}: ${result.wuxingCount[wuxing]}`}
                    </Tag>
                  ))}
                </div>
              )}
              {!result && <Text className="text-white/85">待计算</Text>}
            </div>
            <div className="flex items-center">
              <Text className="text-white/60 w-16">生肖：</Text>
              <Text className="text-white/85">
                {result ? result.zodiac : '待计算'}
              </Text>
            </div>
          </div> 